#
# run with r CMD BATCH graph-branch-prediction.r
#
# Disclaimer: This works on my Mac. I'm not an R expert, so no guarantees...
#

# if not available, install in an R shell via: install.packages("ggplot2")
library("ggplot2")

df <- data.frame(
				 Approach = factor(c("No random elements", "25% elements random", "50% elements random", "All elements random")), 
                 Throughput = c(23.35, 12.73, 9.35, 5.83)
                 )



# basic setup
gg <- ggplot(data=df, aes(x=reorder(Approach, -Throughput),y=Throughput,fill=Approach)) + geom_bar(stat="identity", width=0.4, position=position_dodge())
# set appearence/labels
gg <- gg + xlab("Approach") + ylab("Mean Throughput [op/ms]")+labs(color = "Approach")+theme_bw()+ggtitle("Mean throughput (2^16 elements)")+theme(axis.text.x = element_blank())+scale_fill_brewer(palette="Set1", limits=c("No random elements", "25% elements random", "50% elements random", "All elements random"))

ggsave(file="branch-prediction.png", plot=gg, width=6, height=4.5, dpi=200)
