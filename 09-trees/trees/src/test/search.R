require(ggplot2)
library(ggpubr)

palette <- c("#89C5DA", "#DA5724", "#74D944", "#CE50CA", "#3F4921", "#C0717C", "#CBD588", "#5F7FC7",
"#673770", "#D3D93E", "#38333E", "#508578", "#D7C1B1", "#689030", "#AD6F3B", "#CD9BCD",
"#D14285", "#6DDE88", "#652926", "#7FDCC0", "#C84248", "#8569D5", "#5E738F", "#D1A33D",
"#8A7C64", "#599861")


tbl <- read.csv("search.csv", header = TRUE, sep = ';')

time <- ggplot(tbl, aes(x = search_count, y = ms,
group = interaction(case),
colour = case)) + geom_point() + geom_line() + theme(legend.text=element_text(size=6)) +
scale_fill_manual(values=palette) + scale_colour_manual(values=palette)


h <- ggplot(tbl, aes(x = search_count, y = height,
group = interaction(case),
colour = case)) + geom_point() + geom_line() + theme(legend.text=element_text(size=6)) +
    scale_fill_manual(values=palette) + scale_colour_manual(values=palette)

ggarrange(time, h, ncol=1, nrow=2, common.legend = TRUE, legend="top")

ggsave("search.png")
