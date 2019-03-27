awk '{print "/**" $NF "**/\n private String " $1 ";\n"}' 1.txt
