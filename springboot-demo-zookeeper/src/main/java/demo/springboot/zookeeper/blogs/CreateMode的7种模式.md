
## 看源码，CreateMode 有如下7种
 
 ```java
public enum CreateMode {
    /**
     * The znode will not be automatically deleted upon client's disconnect.
     */
    PERSISTENT (0, false, false, false, false),
    /**
     * The znode will not be automatically deleted upon client's disconnect,
     * and its name will be appended with a monotonically increasing number.
     */
    PERSISTENT_SEQUENTIAL (2, false, true, false, false),
    /**
     * The znode will be deleted upon the client's disconnect.
     */
    EPHEMERAL (1, true, false, false, false),
    /**
     * The znode will be deleted upon the client's disconnect, and its name
     * will be appended with a monotonically increasing number.
     */
    EPHEMERAL_SEQUENTIAL (3, true, true, false, false),
    /**
     * The znode will be a container node. Container
     * nodes are special purpose nodes useful for recipes such as leader, lock,
     * etc. When the last child of a container is deleted, the container becomes
     * a candidate to be deleted by the server at some point in the future.
     * Given this property, you should be prepared to get
     * {@link org.apache.zookeeper.KeeperException.NoNodeException}
     * when creating children inside of this container node.
     */
    CONTAINER (4, false, false, true, false),
    /**
     * The znode will not be automatically deleted upon client's disconnect.
     * However if the znode has not been modified within the given TTL, it
     * will be deleted once it has no children.
     */
    PERSISTENT_WITH_TTL(5, false, false, false, true),
    /**
     * The znode will not be automatically deleted upon client's disconnect,
     * and its name will be appended with a monotonically increasing number.
     * However if the znode has not been modified within the given TTL, it
     * will be deleted once it has no children.
     */
    PERSISTENT_SEQUENTIAL_WITH_TTL(6, false, true, false, true);
    
    
   ```