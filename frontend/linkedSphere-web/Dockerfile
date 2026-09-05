# ---------------------------------------------------------
# Stage 1: Build React application
# ---------------------------------------------------------

FROM node:22-alpine AS builder

WORKDIR /app

# Enable pnpm via corepack (matches local dev tool)
RUN corepack enable && corepack prepare pnpm@latest --activate

# Copy dependency manifests first for layer caching
COPY package.json pnpm-lock.yaml ./

# Install all dependencies (frozen to match lockfile)
RUN pnpm install --frozen-lockfile

# Copy the full source (includes tsconfig.json, vite.config.ts, etc.)
COPY . .

# Build the production bundle
RUN pnpm run build


# ---------------------------------------------------------
# Stage 2: Serve with nginx (production-grade static server)
# ---------------------------------------------------------

FROM nginx:alpine

# Copy built assets from the builder stage
COPY --from=builder /app/dist /usr/share/nginx/html

# Copy nginx config for SPA routing (handles client-side routes)
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]