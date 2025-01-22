docker run --name mysql-server \
  -e MYSQL_ROOT_PASSWORD=8uhb^YJm \
  -e MYSQL_DATABASE=rookie-im-server \
  -p 3306:3306 \
  -v /my/custom/data:/var/lib/mysql \
  -v /my/custom/conf:/etc/mysql/conf.d \
  -d mysql:8.0