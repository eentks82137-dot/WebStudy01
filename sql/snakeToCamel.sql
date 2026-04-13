select 'private '
       ||
       case
          when data_type in ( 'NUMBER' ) then
             'Integer '
          when data_type in ( 'VARCHAR2',
                              'CHAR' ) then
             'String '
          when data_type in ( 'DATE',
                              'TIMESTAMP' ) then
             'LocalDate '
          else
             'String '
       end
       || replace(
   substr(
      initcap('a' || column_name),
      2
   ),
   '_',
   ''
)
       || ';' as field_declaration
  from cols
 where table_name = 'REVIEW'