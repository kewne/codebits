drop table if exists WORDS;
create table WORDS (
		W_ID INTEGER PRIMARY KEY autoincrement,
		WORD varchar(50)
);
.mode csv
.import dictionary.txt WORDS_TEMP
insert into WORDS(WORD) select * from WORDS_TEMP;
drop table WORDS_TEMP;
