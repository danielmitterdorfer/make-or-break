#
# run with r CMD BATCH graph-false-sharing.r
#
# Disclaimer: This works on my Mac. I'm not an R expert, so no guarantees...
#

# if not available, install in an R shell via: install.packages("ggplot2")
library("ggplot2")

df <- data.frame(
				 Approach = factor(c("@Contented", "@Contented", "False Sharing","False Sharing")),
				 Operation = factor(c("Read", "Write", "Read", "Write")),
                 Throughput = c(2404.422, 359.385, 919.915, 359.429)
                 )

# basic setup
gg <- ggplot(data=df, aes(x=Approach,fill=Operation,weight=Throughput)) + geom_bar(width=0.4, position=position_dodge())
# set appearence/labels
gg <- gg + ylab("Mean Throughput [op/µs]")+theme_bw()+ggtitle("Throughput comparison (3 readers, 1 writer)")

ggsave(file="false-sharing.png", plot=gg, width=4.7, height=3.5, dpi=200)
