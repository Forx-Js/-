sed '/public void set/!d' $1 | sed 's/public void /po./; s/{/;/' | sed 's/([^ ]*/(param.get__/; s/)/())/' | sed 's/__ ./\U&/; s/__ //'

