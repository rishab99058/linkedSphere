#!/usr/bin/env bash

set -euo pipefail

echo "Detecting frontend changes..."

BASE_SHA="${1:-HEAD~1}"
HEAD_SHA="${2:-HEAD}"

changed_files=$(git diff --name-only "$BASE_SHA" "$HEAD_SHA")

echo
echo "Changed files:"
printf '%s\n' "$changed_files"

echo
echo "Analyzing frontend changes..."

frontend_changed=false

while IFS= read -r file; do

    # Ignore everything outside frontend
    if [[ ! "$file" =~ ^frontend/ ]]; then
        continue
    fi

    frontend_changed=true

done <<< "$changed_files"


# -------------------------------------------------------------
# Final result
# -------------------------------------------------------------

echo
echo "Frontend changed: $frontend_changed"

if [[ "$frontend_changed" == true ]]; then

    echo
    echo "Frontend changes detected."

    # GitHub Actions output
    if [[ -n "${GITHUB_OUTPUT:-}" ]]; then
        echo "frontend_changed=true" >> "$GITHUB_OUTPUT"
    fi

else

    echo
    echo "No frontend changes detected."

    # GitHub Actions output
    if [[ -n "${GITHUB_OUTPUT:-}" ]]; then
        echo "frontend_changed=false" >> "$GITHUB_OUTPUT"
    fi

fi