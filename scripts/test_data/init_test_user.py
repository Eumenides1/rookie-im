from faker import Faker
import json
import string

# Initialize Faker with Chinese locale
fake = Faker("zh_CN")

# Generate 20 Chinese test users with nicknames like 测试用户A, 测试用户B, etc.
test_users = []
for i, char in enumerate(string.ascii_uppercase[:20]):  # Generate 20 users
    user = {
        "nickName": f"测试用户{char}",
        "photo": fake.image_url(),
        "userSex": fake.random_int(min=0, max=2),
        "phone": fake.phone_number(),
        "email": fake.email(),
        "birthDay": fake.date_of_birth().strftime("%Y-%m-%d"),
        "location": fake.city(),
        "selfSignature": fake.sentence(nb_words=6),
        "extra": {"hobby": fake.word(), "skill": fake.word()},  # Avoid double JSON encoding
        "password": fake.password(length=8),
        "friendAllowType": fake.random_int(min=0, max=2),
        "disableAddFriend": fake.random_int(min=0, max=1),
        "userType": fake.random_int(min=0, max=2)
    }
    test_users.append(user)

# Construct the ImportUserReq payload
import_user_req = {
    "userData": test_users
}

# Output as JSON with ensure_ascii=False to avoid Unicode escape sequences
import_user_json = json.dumps(import_user_req, ensure_ascii=False, indent=4)
print(import_user_json)