#LPSDN Simulator

##Sink-Node Location Privacy in WSNs via SDN Approach

In a WSN, sink-node gathers data from surrounding nodes and sends it to outside world via a gateway. Therefore, its location information is important to both attacker and network operator. The former can launch attacks on a sink-node to steal information or damage it, while the latter must hide its location to ensure data’s safety, and physical protection. Providing sink-node location information anonymity in WSN against a local and global adversary with minimal energy penalties, we propose a novel technique Location Privacy via Software Defined Networking (LPSDN), inspired by the concept of Software Defined Networking. LPSDN uses three kinds of
nodes: a centralized controller, a special buffering and forwarding node, and a slave node. These nodes work together to hide sinknode location information against traffic analysis attack. LPSDN conserves more energy, because it does not generate fake packets to hide sink-node location information. A GUI/command-line can
be used to monitor (energy status, traffic density) and control the whole network. Comparing with traditional WSNs, LPSDN efficiently reduces processing burden on slave nodes, increases overall network life, and creates on-demand traffic density zones to deceive an adversary. It also provides better maintainability,manageability, and control over all nodes and their behavior.
For more details refer to paper ***[Sink-Node Location Privacy in WSNs via SDN Approach](https://ieeexplore.ieee.org/stamp/stamp.jsp?tp=&arnumber=7549407)***,this repository is a simple simulator for LPSDN implement by Java.


## run LPSDN Simulator

- git clone https://github.com/ShijunDeng/Lpsdn-Simulator.git
- revise configuration in com.sim.utils.RunUtil
- run main.java

Make sure that your jdk version is at least 1.8 or later