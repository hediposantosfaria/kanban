CREATE TABLE IF NOT EXISTS responsavel (
  id BIGSERIAL PRIMARY KEY,
  nome VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS projeto (
  id BIGSERIAL PRIMARY KEY,
  nome VARCHAR(255) NOT NULL,
  status VARCHAR(40) NOT NULL,
  inicio_previsto DATE,
  termino_previsto DATE,
  inicio_realizado DATE,
  termino_realizado DATE,
  responsavel_id BIGINT REFERENCES responsavel(id),
  created_at TIMESTAMP NOT NULL DEFAULT NOW(),
  updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);
CREATE INDEX IF NOT EXISTS idx_projeto_status ON projeto(status);
