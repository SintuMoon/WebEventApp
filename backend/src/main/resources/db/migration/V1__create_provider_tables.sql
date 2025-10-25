CREATE TABLE provider (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(100),
    location VARCHAR(255),
    description TEXT,
    email VARCHAR(255),
    phone VARCHAR(50),
    instagram VARCHAR(255),
    whatsapp VARCHAR(255),
    website VARCHAR(255)
);

CREATE TABLE provider_media (
    id SERIAL PRIMARY KEY,
    provider_id INT NOT NULL REFERENCES provider(id) ON DELETE CASCADE,
    url TEXT NOT NULL,
    sort_order INT DEFAULT 0
);

CREATE TABLE provider_clicks (
    id SERIAL PRIMARY KEY,
    provider_id INT NOT NULL REFERENCES provider(id) ON DELETE CASCADE,
    type VARCHAR(50) NOT NULL,  -- 'whatsapp', 'instagram', 'email'
    created_at TIMESTAMP DEFAULT NOW()
);
