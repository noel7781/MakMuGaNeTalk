INSERT INTO user (user_id, email, password)
VALUES
    (1, 'first@gmail.com', 'password'),
    (2, 'seconde@gmail.com', 'password'),
    (3, 'third@gmail.com', 'password');

INSERT INTO chat_room (chat_room_id, type, title, owner_user_id, password, created_by, updated_by)
VALUES
    (1, 'OPEN_CHAT', '1번 수다수다', 1, '123', 1, 1),
    (2, 'OPEN_CHAT', '2번 잡담잡담', 2, '123', 1, 1),
    (3, 'OPEN_CHAT', '3번 웅성웅성', 3, '123', 1, 1);