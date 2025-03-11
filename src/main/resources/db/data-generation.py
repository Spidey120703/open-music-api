import random

userInsertFormatter = "INSERT INTO "
                    + "    `user` (`username`, `password`, `avatar`, `nickname`, `email`, `phone`, `role_id`) "
                    + "VALUES "
                    + "    ('{username}', '{password}', '', '{nickname}','{email}', '{phone}', {role})"

for i in range(1, 121):
    userInsert = userInsertFormatter.format(
        username=f"user{i}",
        password="12345678",
        nickname=f"User {i}",
        email="user{i}@open-music.com",
        phone="1234567890" + str(i),
        role=2
    )
    print(userInsert)