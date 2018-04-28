CREATE TABLE "authorization"
(
	id bigserial PRIMARY KEY,
	username text NOT NULL,
	"password" varchar(50) NOT NULL,
	role text NOT NULL DEFAULT 'ROLE_USER',
	is_active boolean NOT NULL DEFAULT true,
	
	CONSTRAINT authorization_unique UNIQUE(username)
);