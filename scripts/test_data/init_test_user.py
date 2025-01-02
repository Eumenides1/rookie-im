from faker import Faker
import json
import string

# Initialize Faker with Chinese locale
fake = Faker("zh_CN")

# Function to generate test user
def generate_test_user(index):
    char = string.ascii_uppercase[index % 26]  # Cycle through A-Z for user suffix
    user = {
        "nickName": f"测试用户{char}{index}",
        "photo": fake.image_url(),
        "userSex": fake.random_int(min=0, max=2),
        "phone": fake.phone_number(),
        "email": fake.email(),
        "birthDay": fake.date_of_birth().strftime("%Y-%m-%d"),
        "location": fake.city(),
        "selfSignature": fake.sentence(nb_words=6),
        "password": fake.password(length=8),
        "friendAllowType": fake.random_int(min=0, max=2),
        "disableAddFriend": fake.random_int(min=0, max=1),
        "userType": fake.random_int(min=0, max=2)
    }
    return user

# Generate 10,000 test users
test_users = [generate_test_user(i) for i in range(10000)]

# Construct the ImportUserReq payload
import_user_req = {
    "userData": test_users
}

# Output as JSON with ensure_ascii=False to avoid Unicode escape sequences
import_user_json = json.dumps(import_user_req, ensure_ascii=False, indent=4)

# Save to a file for later use
with open("import_users_10000.json", "w", encoding="utf-8") as file:
    file.write(import_user_json)

print("10万条测试数据生成完毕，已保存到 import_users_10000.json 文件。")