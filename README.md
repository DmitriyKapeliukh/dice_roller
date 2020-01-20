# Dice Roller

### The rolls distribution of one dice with number of rolls 1001, 5001, 10001

The each side of dice could have a similar chance and the deviation the deviation will decrease with each new throw.

### The rolls distribution of two dice with number of rolls 1001, 5001, 10001

Deviation for two dice completely different. The sides "2" and "12" have the least chance of being thrown,
because they have only one out, "1" + "1" and "6" + "6". Accordingly "7" are most likely to be thrown because
the are 6 outs, "1" + "6", "2" + "5", "3" + "4", "5" + "2", "6" + "1".

# ![Two dice deviation chart](src/main/resources/Dice_Distribution_(bar).png)

### Run Tests

mvn clean verify

### Report

${user.dir}/target/site/serenity/index.html