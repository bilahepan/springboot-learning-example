
##  EventType 事件的7种类型

```java

public enum EventType {
    None (-1),//Disconnected0）;Expired(-112) ;AuthFailed(4) ; 这几种状态都会触发这个类型的监听事件
    NodeCreated (1),
    NodeDeleted (2),
    NodeDataChanged (3),
    NodeChildrenChanged (4),
    DataWatchRemoved (5),
    ChildWatchRemoved (6);

```