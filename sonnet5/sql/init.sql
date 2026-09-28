CREATE TABLE IF NOT EXISTS items (
  id          SERIAL PRIMARY KEY,
  name        TEXT NOT NULL,
  description TEXT,
  created_at  TIMESTAMPTZ NOT NULL DEFAULT now()
);

INSERT INTO items (name, description)
SELECT * FROM (VALUES
  ('First item', 'This is the first seeded item'),
  ('Second item', 'This is the second seeded item'),
  ('Third item', 'This is the third seeded item')
) AS seed(name, description)
WHERE NOT EXISTS (SELECT 1 FROM items);
