insert into users (email, password, fullname) values 
  ('admin@eshop.org', 'admin', 'Admin Admin'),
  ('user@eshop.org', 'user', 'John Doe');

insert into roles (role) values 
  ('ROLE_ADMIN'),
  ('ROLE_USER');

insert into users_roles (users_id, roles_id) values 
  (1, 1), (1, 2),
  (2, 2);

select u.email, u.fullname, r.role from users u join users_roles ur on u.id=ur.users_id join roles r on ur.roles_id=r.id;