#
# run with r CMD BATCH graph-branch-prediction.r
#
# Disclaimer: This works on my Mac. I'm not an R expert, so no guarantees...
#

# if not available, install in an R shell via: install.packages("ggplot2")
library("ggplot2")

df <- data.frame(
				 Approach = factor(c("Sorted", "Unsorted")), 
                 Throughput = c(1332.453, 387.044)
                 )

# basic setup
gg <- ggplot(data=df, aes(x=Approach,y=Throughput,fill=Approach)) + geom_bar(stat="identity", width=0.4, position=position_dodge())
# set appearence/labels
gg <- gg + ylab("Mean Throughput [op/µs]")+labs(color = "Approach")+theme_bw()+ggtitle("Mean throughput (2^16 elements)")

ggsave(file="branch-prediction.png", plot=gg, width=6, height=4.5, dpi=200)
