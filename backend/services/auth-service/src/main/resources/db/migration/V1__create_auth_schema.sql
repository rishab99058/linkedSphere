CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- =====================================================
-- USERS
-- =====================================================

CREATE TABLE IF NOT EXISTS users
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20),

    provider VARCHAR(30) NOT NULL DEFAULT 'LOCAL',
    provider_id VARCHAR(255),

    email_verified BOOLEAN NOT NULL DEFAULT FALSE,

    account_status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',

    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uk_users_email UNIQUE (email),
    CONSTRAINT uk_users_phone_number UNIQUE (phone_number),

    CONSTRAINT chk_users_provider
        CHECK (provider IN ('LOCAL', 'GOOGLE')),

    CONSTRAINT chk_users_status
        CHECK (account_status IN ('ACTIVE', 'LOCKED', 'DISABLED'))
);

-- =====================================================
-- ROLES
-- =====================================================

CREATE TABLE IF NOT EXISTS roles
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    name VARCHAR(50) NOT NULL,
    description VARCHAR(255),

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uk_roles_name UNIQUE (name)
);

-- =====================================================
-- USER ROLES
-- =====================================================

CREATE TABLE IF NOT EXISTS user_roles
(
    user_id UUID NOT NULL,
    role_id UUID NOT NULL,

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT pk_user_roles PRIMARY KEY (user_id, role_id),

    CONSTRAINT fk_user_roles_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_user_roles_role
        FOREIGN KEY (role_id)
        REFERENCES roles(id)
        ON DELETE RESTRICT
);

-- =====================================================
-- DEFAULT ROLES
-- =====================================================

INSERT INTO roles (name, description)
VALUES
('ROLE_USER', 'Default user role'),
('ROLE_ADMIN', 'System administrator'),
('ROLE_RECRUITER', 'Recruiter role')
ON CONFLICT (name) DO NOTHING;
