#
# run with r CMD BATCH graph-prefetching.r
#
# Disclaimer: This works on my Mac. I'm not an R expert, so no guarantees...
#

# if not available, install in an R shell via: install.packages("ggplot2")
library("ggplot2")

df <- data.frame(
				 Component = factor(c("Array (Plain)", "ArrayList", "LinkedList")),
                 Throughput = c(81.62, 25.75, 6.74)
                 )

# basic setup
gg <- ggplot(data=df, aes(x=Component,y=Throughput,fill=Component)) + geom_bar(stat="identity", width=0.4, position=position_dodge())
# set appearence/labels
gg <- gg + ylab("Mean Throughput [op/ms]")+labs(color = "Implementation")+theme_bw()+ggtitle("Mean throughput for linear traversal (2^15 list elements)")

ggsave(file="traversal-throughput.png", plot=gg, width=6, height=4.5, dpi=200)
