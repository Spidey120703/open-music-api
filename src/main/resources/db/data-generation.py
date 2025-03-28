import random

def random_numbers():
    prefixes = {
        'usim': {
            'ct': [133, 149, 153, 173, 177, 180, 181, 189, 190, 191, 193, 199],
            'cm': [* range(1340, 1349), 135, 136, 137, 138, 139, 147, 150, 151, 152, 157, 158, 159, 178, 182, 183, 184, 187, 188, 195, 197, 198],
            'cu': [130, 131, 132, 145, 155, 156, 166, 175, 176, 185, 186, 196],
            'cb': [192,]
        },
        'satellite': {
            'ct': [1349, * range(17400, 17406)],
            'ctt': [1749],
            'sn': [1619],
        },
        'IoT': {
            'ct': [1410,],
            'cm': [1440, 1441, 148, 172],
            'cu': [1400, 146],
        },
        'mvno': {
            'IoT': {
                'cu': [10641,]
            },
            'usim': {
                'ct': [162, * map(lambda x: 1700 + x, (0, 1, 2))],
                'cm': [165, * map(lambda x: 1700 + x, (3, 5, 6))],
                'cu': [167, * map(lambda x: 1700 + x, (4, 7, 8, 9)), 171]
            }
        },
        'reserved': {
            'IoT': {
                'cu': [* range(14010, 14100)],
                'ct': [* range(14110, 14200)],
                None: [* range(14200, 14400)],
                'cm': [* range(14420, 14500)]
            },
            'satellite': [ * range(17413, 17490) ],
            None: [154, 161, 164, 194, * range(920, 930), * range(980, 990)]
        }
    }

    random_digits = lambda x: ''.join(str(random.randint(0, 9)) for _ in range(x))

    def random_prefix(_type):
        operator = random.choice(list(prefixes[_type].keys()))
        prefix = random.choice(prefixes[_type][operator])
        return str(prefix), operator

    class Anonymous:

        @staticmethod
        def mobile():
            xxx, op = random_prefix('usim')
            yyyy = random_digits(7 - len(xxx))
            zzzz = random_digits(4)
            return xxx + yyyy + zzzz, op

        @staticmethod
        def IoT():
            xxxxx, op = random_prefix('IoT')
            yyyy = random_digits(9 - len(xxxxx))
            zzzz = random_digits(4)
            return xxxxx + yyyy + zzzz, op

    return Anonymous

userInsertFormatter = "INSERT INTO \n" \
                    + "    `user` (`username`, `password`, `avatar`, `nickname`, `email`, `phone`, `role_id`) \n" \
                    + "VALUES \n" \
                    + "    ('{username}', '{password}', '', '{nickname}','{email}', '{phone}', {role});\n"

for i in range(1, 121):
    print(userInsertFormatter.format(
        username=f"user{i}",
        password="12345678",
        nickname=f"User {i}",
        email=f"user{i}@open-music.com",
        phone=random_numbers().mobile()[0],
        role=random.randint(1, 2)
    ))