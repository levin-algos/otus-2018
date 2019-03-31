require(ggplot2)
library(ggpubr)

tbl <- read.csv("search.csv", header = TRUE, sep = ';')

time <- ggplot(tbl, aes(x = size, y = ms,
group = interaction(Case),
colour = Case)) + geom_point() + geom_line()


h <- ggplot(tbl, aes(x = size, y = found,
group = interaction(Case),
colour = Case)) + geom_point() + geom_line()

ggarrange(time, h, ncol=2, nrow=2, common.legend = TRUE, legend="top")

ggsave("search.png")
