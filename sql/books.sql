create table author (
   author_id   number,
   author_name varchar2(100) not null,
   birth_date  date,
   nationality varchar2(50),
   constraint pk_author primary key ( author_id )
);

create table publisher (
   publisher_id   number,
   publisher_name varchar2(100) not null,
   address        varchar2(200),
   phone          varchar2(20),
   constraint pk_pulbisher primary key ( publisher_id )
);

create table book (
   book_id          number,
   title            varchar2(200) not null,
   author_id        number not null,
   publisher_id     number not null,
   isbn             varchar2(13),
   publication_date date,
   price            number(10,2),
   description      clob,
   constraint pk_book primary key ( book_id ),
   constraint fk_book_author foreign key ( author_id )
      references author ( author_id ),
   constraint fk_book_publisher_id foreign key ( publisher_id )
      references publisher ( publisher_id )
);

create table review (
   review_id     number,
   book_id       number not null,
   reviewer_name varchar2(50) not null,
   rating        number(1) not null,
   content       varchar2(1000),
   created_date  date default sysdate,
   constraint pk_review primary key ( review_id ),
   constraint fk_review_book foreign key ( book_id )
      references book ( book_id )
);

commit;