CREATE TABLE "authorization"
(
	user_id bigint PRIMARY KEY,
	username text NOT NULL,
	"password" varchar(50) NOT NULL,
	role text NOT NULL,
	is_active boolean DEFAULT true
);