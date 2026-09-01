#!/usr/bin/env bash

set -euo pipefail

echo "Detecting affected services..."

BASE_SHA="${1:-HEAD~1}"
HEAD_SHA="${2:-HEAD}"

changed_files=$(git diff --name-only "$BASE_SHA" "$HEAD_SHA")

echo
echo "Changed files:"
printf '%s\n' "$changed_files"

echo
echo "Analyzing backend changes..."

services=""
common_changed=false
parent_changed=false

while IFS= read -r file; do

    # Ignore everything outside backend
    if [[ ! "$file" =~ ^backend/ ]]; then
        continue
    fi

    # Common module changed
    if [[ "$file" =~ ^backend/modules/common/ ]]; then
        common_changed=true
    fi

    # Parent POM changed
    if [[ "$file" == "backend/pom.xml" ]]; then
        parent_changed=true
    fi

    # Direct service change
    if [[ "$file" =~ ^backend/services/([^/]+)/ ]]; then

        service="${BASH_REMATCH[1]}"

        case ",$services," in
            *,"$service",*)
                ;;
            *)
                if [[ -z "$services" ]]; then
                    services="$service"
                else
                    services="$services,$service"
                fi
                ;;
        esac

    fi

done <<< "$changed_files"


# -------------------------------------------------------------
# Common module affects these services
# -------------------------------------------------------------

if [[ "$common_changed" == true ]]; then

    for service in \
        auth-service \
        user-service \
        notification-service
    do

        case ",$services," in
            *,"$service",*)
                ;;
            *)
                if [[ -z "$services" ]]; then
                    services="$service"
                else
                    services="$services,$service"
                fi
                ;;
        esac

    done

fi


# -------------------------------------------------------------
# Parent POM affects every backend service
# -------------------------------------------------------------

if [[ "$parent_changed" == true ]]; then

    for service in \
        auth-service \
        user-service \
        notification-service \
        gateway-service \
        discovery-server \
        post-service
    do

        case ",$services," in
            *,"$service",*)
                ;;
            *)
                if [[ -z "$services" ]]; then
                    services="$service"
                else
                    services="$services,$service"
                fi
                ;;
        esac

    done

fi


# -------------------------------------------------------------
# Final result
# -------------------------------------------------------------

echo
echo "Common changed: $common_changed"
echo "Parent POM changed: $parent_changed"

echo
echo "Affected services:"

if [[ -n "$services" ]]; then

    affected_services=$(printf '%s\n' "$services" | tr ',' '\n')

    printf '%s\n' "$affected_services"

    # GitHub Actions output
    if [[ -n "${GITHUB_OUTPUT:-}" ]]; then
        {
            echo "affected_services<<EOF"
            printf '%s\n' "$affected_services"
            echo "EOF"
        } >> "$GITHUB_OUTPUT"
    fi

else

    echo "No backend services affected."

    # GitHub Actions output
    if [[ -n "${GITHUB_OUTPUT:-}" ]]; then
        echo "affected_services=" >> "$GITHUB_OUTPUT"
    fi

fi