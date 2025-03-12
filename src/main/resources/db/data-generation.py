import random

userInsertFormatter = "INSERT INTO " \
                    + "    `user` (`username`, `password`, `avatar`, `nickname`, `email`, `phone`, `role_id`) " \
                    + "VALUES " \
                    + "    ('{username}', '{password}', '', '{nickname}','{email}', '{phone}', {role});"

for i in range(1, 121):
    userInsert = userInsertFormatter.format(
        username=f"user{i}",
        password="12345678",
        nickname=f"User {i}",
        email=f"user{i}@open-music.com",
        phone="12345678901",
        role=2
    )
    print(userInsert)
