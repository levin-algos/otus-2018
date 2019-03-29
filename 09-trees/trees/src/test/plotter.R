require(ggplot2)


tbl <- read.csv("insertion.csv", header = TRUE, sep = ';')
# classes <- unique(tbl$Class)
# types <- unique(tbl$Test.type)

ggplot(tbl, aes(x = size, y = ms,
    group = interaction(Case),
    colour = Case)) + geom_point() + geom_line()

ggsave("1.png");
# plotlist <- ggplot();
# for (cl in classes) {
#     classSubset <- subset(tbl, subset =(Class == cl))
#     for (type in types) {
#         typeSubset <- subset(classSubset, subset=(Test.type == type))
#         g <- ggplot(typeSubset, aes(typeSubset$Size, typeSubset$Delta..ms.)) + geom_line()
#         multiplot(g, plotlist)
#     }
# }