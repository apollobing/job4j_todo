ALTER TABLE tasks
ADD user_id int not null references users(id);
