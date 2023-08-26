INSERT INTO users (id, first_name, last_name, email, password, roles, is_active)
VALUES (1, 'Joe', 'Doe', 'admin@admin.com', '$2a$10$c/lk2nVWorQqJsbbDcLIDeLnteufRkf343NY8jaaF3oD//mwxmT6K', '{"USER", "ADMIN"}', true),
       (2, 'Jessica', 'Doe', 'user@user.com', '$2a$10$c/lk2nVWorQqJsbbDcLIDeLnteufRkf343NY8jaaF3oD//mwxmT6K', '{"USER"}', true);

SELECT pg_catalog.setval('public.user_id_seq', 2, true);

INSERT INTO posts (id, title, slug, content, user_id, is_visible, is_deleted, created_at, updated_at)
VALUES (1, 'Sunt Aut Facere Repellat Provident', 'sunt-aut-facere-repellat-provident', 'uia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto', 1, true, false, '2023-08-25 13:00:00.000', '2023-08-25 13:00:00.00'),
       (2, 'Qui Est Esse', 'qui-est-esse', 'ullam et saepe reiciendis voluptatem adipisci\nsit amet autem assumenda provident rerum culpa\nquis hic commodi nesciunt rem tenetur doloremque ipsam iure\nquis sunt voluptatem rerum illo velit', 2, true, false, '2023-08-26 17:40:00.000', '2023-08-26 17:40:00.00');

SELECT pg_catalog.setval('public.post_id_seq', 2, true);

INSERT INTO comments (id, comment, post_id, user_id, is_deleted, created_at)
VALUES (1, 'Good Job!', 2, 1, false, '2023-08-25 15:00:00.000'),
       (2, 'Awesome!', 1, 2, false, '2023-08-26 18:00:00.000');

SELECT pg_catalog.setval('public.comment_id_seq', 2, true);

