
select
      board_no
    , board_title
    , board_writer
    , count
    , create_date
    , origin_name
 from board
 where status = 'Y'
 order 
    by board_no desc; 