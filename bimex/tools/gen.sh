
# gen resultMap
awk '{
    print "<result column=\"" $1 "\" property=\"__" $1 "\"/>"
}' < 1.txt | sed 's/__./\L&/g; s/__//g'

echo "--------------------------------"
# gen all columns
awk 'BEGIN{print "<sql id=#allColumns#>"}
{
    print "\t" $1 ","
}
END{ print "</sql>" }' < 1.txt | sed 's/#/"/g'

echo "--------------------------------"
# gen all columns
awk '{IGNORECASE=1}
    {
    v = "private "
    if($2~/char/){
        v = (v"String")
    }
    else if($2~/int/){
        if($1~/^is/) {
            v = (v"Boolean")
        } else {
            v = (v"Integer")
        }
    }
    else if($2~/date/){
        v = (v"Date")
    }
    else if($2~/bit/){
        v = (v"Integer")
        if($1~/^is/) {
            v = (v"Boolean")
        } else {
            v = (v"Integer")
        }
    }
    else if($2~/numeric/){
        v = (v"Double")
    }
    else if($2~/decimal/){
        v = (v"Double")
    }

    if($0~/--/) {
        print "/**";
        print "* " $NF;
        print "*/";
    }
    print v " __" $1 ";"
}' < 1.txt | sed 's/__./\L&/g; s/__//g'

echo "--------------------------------"
# gen all columns
awk '{IGNORECASE=1}
{
    if($2~/datetime/){
        print "#{__" $1 ", javaType=java.util.Date, jdbcType=TIMESTAMP},"
    }
    else if($2~/date/){
        print "#{__" $1 ", javaType=java.util.Date, jdbcType=DATE},"
    }
    else {
        print "#{__" $1 "},"
    }
}' < 1.txt | sed 's/__./\L&/g; s/__//g'



echo "--------------------------------"
# gen update all columns
awk '{IGNORECASE=1} BEGIN{print "UPDATE __ SET "}
{
    if($2~/datetime/){
        print "\t" $1 " = #{__" $1 ", javaType=java.util.Date, jdbcType=TIMESTAMP},"
    }
    else if($2~/date/){
        print "\t" $1 " = #{__" $1 ", javaType=java.util.Date, jdbcType=DATE},"
    }
    else {
        print "\t" $1 " = #{__" $1 "},"
    }
}' < 1.txt | sed 's/__./\L&/g; s/__//g'



