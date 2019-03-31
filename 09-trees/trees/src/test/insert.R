require(ggplot2)
library(ggpubr)

tbl <- read.csv("insertion.csv", header = TRUE, sep = ';')

time <- ggplot(tbl, aes(x = size, y = ms,
    group = interaction(Case),
    colour = Case)) + geom_point() + geom_line()


h <- ggplot(tbl, aes(x = size, y = height,
group = interaction(Case),
colour = Case)) + geom_point() + geom_line()

left_rot <- ggplot(tbl, aes(x = size, y = l_rot,
group = interaction(Case),
colour = Case)) + geom_point() + geom_line()

right_rot <- ggplot(tbl, aes(x = size, y = r_rot,
group = interaction(Case),
colour = Case)) + geom_point() + geom_line()



ggarrange(time, h, left_rot, right_rot, ncol=2, nrow=2, common.legend = TRUE, legend="top")

ggsave("1.png")
