CREATE TABLE users (
    id TEXT PRIMARY KEY UNIQUE NOT NULL,
    login TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL UNIQUE,
    role TEXT NOT NULL
)