<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Rookie平台验证码</title>
    <style>
        /* 全局样式 */
        body {
            font-family: 'Arial', sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f9;
            color: #333;
            line-height: 1.6;
        }
        .email-container {
            max-width: 600px;
            margin: 0 auto;
            background: #ffffff;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            overflow: hidden;
        }
        .email-header {
            background-color: #4CAF50;
            padding: 20px;
            text-align: center;
        }
        .email-header h1 {
            color: #ffffff;
            margin: 0;
            font-size: 24px;
        }
        .email-body {
            padding: 20px;
        }
        .email-body p {
            margin: 10px 0;
            font-size: 16px;
        }
        .verification-code {
            display: inline-block;
            font-size: 24px;
            color: #4CAF50;
            font-weight: bold;
            background: #f4f4f9;
            border: 2px dashed #4CAF50;
            padding: 10px 20px;
            border-radius: 8px;
            margin: 20px 0;
        }
        .email-footer {
            background-color: #f4f4f9;
            text-align: center;
            padding: 10px;
            font-size: 12px;
            color: #999;
        }
        .email-footer a {
            color: #4CAF50;
            text-decoration: none;
        }
    </style>
</head>
<body>
<div class="email-container">
    <!-- 邮件头部 -->
    <div class="email-header">
        <h1>Your Verification Code</h1>
    </div>

    <!-- 邮件内容 -->
    <div class="email-body">
        <p>Hi <strong>${user}</strong>,</p>
        <p>Thank you for signing up or logging in. Use the following verification code to complete your request:</p>
        <div class="verification-code">${code}</div>
        <p>If you did not request this, please ignore this email or contact support if you have any concerns.</p>
        <p>Thank you!</p>
    </div>

    <!-- 邮件底部 -->
    <div class="email-footer">
        <p>&copy; 2024 Your Company. All rights reserved.</p>
        <p><a href="https://jaguarliu.me">Visit our website</a></p>
    </div>
</div>
</body>
</html>