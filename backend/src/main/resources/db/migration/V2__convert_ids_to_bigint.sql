-- Align table schemas with JPA entity definitions (Long -> BIGINT)

ALTER TABLE provider_media DROP CONSTRAINT IF EXISTS provider_media_provider_id_fkey;
ALTER TABLE provider_clicks DROP CONSTRAINT IF EXISTS provider_clicks_provider_id_fkey;

ALTER TABLE provider
    ALTER COLUMN id TYPE BIGINT USING id::BIGINT;

ALTER TABLE provider_media
    ALTER COLUMN id TYPE BIGINT USING id::BIGINT,
    ALTER COLUMN provider_id TYPE BIGINT USING provider_id::BIGINT;

ALTER TABLE provider_clicks
    ALTER COLUMN id TYPE BIGINT USING id::BIGINT,
    ALTER COLUMN provider_id TYPE BIGINT USING provider_id::BIGINT;

ALTER TABLE provider_media
    ADD CONSTRAINT provider_media_provider_id_fkey FOREIGN KEY (provider_id) REFERENCES provider(id) ON DELETE CASCADE;

ALTER TABLE provider_clicks
    ADD CONSTRAINT provider_clicks_provider_id_fkey FOREIGN KEY (provider_id) REFERENCES provider(id) ON DELETE CASCADE;
