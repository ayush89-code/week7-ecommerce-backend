-- Test migration (can be rolled back)
CREATE TABLE test_table (id BIGSERIAL PRIMARY KEY, name VARCHAR(50));
INSERT INTO test_table (name) VALUES ('test data');
