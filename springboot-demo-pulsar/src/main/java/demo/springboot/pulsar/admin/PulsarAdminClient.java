package demo.springboot.pulsar.admin;

import org.apache.pulsar.client.admin.PulsarAdmin;
import org.apache.pulsar.client.admin.PulsarAdminException;
import org.apache.pulsar.client.admin.internal.PulsarAdminBuilderImpl;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.common.policies.data.TenantInfo;
import org.apache.pulsar.shade.com.google.common.collect.ImmutableSet;


/**
 * @author: 文若[gaotc@tuya.com]
 * @date: 2019/1/30 下午4:24
 */
public class PulsarAdminClient {
    private static PulsarAdmin admin = null;

    //--web-service-url  对应的是这个参数值，web服务的url
    private static String localClusterUrl = "http://127.0.0.1:8080";

    //集群名
    static String pulsarCluster = "pulsar-cluster";

    //租户 tenant
    static String tenantName = "test-tenant-1";

    //命名空间 namespace
    static String nameSpaceName = tenantName + "/" + "test-namespace-1";

    //主题 topic
    static String topic = "test-topic-1";
    static String topicName = "persistent://" + tenantName + "/" + nameSpaceName + "/" + topic;
    //static String topic = "persistent://my-tenant/my-namespace/my-topic";

    public static void main(String[] args) {
        createTenant();
        createNamespace();
        createTopic();
        closePulsarAdmin();

        //-------------------------------------//

//        deleteTopic();
//        deleteNamespace();
//        deleteTenant();
//        closePulsarAdmin();
    }


    /**
     * 创建租户
     */
    public static void createTenant() {
        try {
            admin = getAdmin();
            TenantInfo tenantInfo = new TenantInfo();
            tenantInfo.setAllowedClusters(ImmutableSet.of(pulsarCluster));
            admin.tenants().createTenant(tenantName, tenantInfo);
        } catch (PulsarAdminException e) {
            System.err.println("--create tenant error!--" + e);
        }
    }


    /**
     * 创建命名空间,并设置集群名
     */
    public static void createNamespace() {
        try {
            //设置TTL时间
            int messageTTL = 86400;
            admin = getAdmin();
            //创建
            admin.namespaces().createNamespace(nameSpaceName);
            //设置ttl
            admin.namespaces().setNamespaceMessageTTL(nameSpaceName, messageTTL);
        } catch (PulsarAdminException e) {
            System.err.println("--create nameSpace error!--" + e);
        }
        //--可以设置bookie的读写确认策略--在此先省略掉
        //设置关联集群,是可以设置多个集群的,所以叫复制集群
        try {
            admin.namespaces().setNamespaceReplicationClusters(nameSpaceName, ImmutableSet.of(pulsarCluster));
        } catch (PulsarAdminException e) {
            System.err.println("--setNamespaceReplicationClusters error!--" + e);
        }
    }


    /**
     * 创建topic
     */
    public static void createTopic() {
        try {
            int numPartitions = 3;
            admin = getAdmin();
            admin.topics().createPartitionedTopic(topicName, numPartitions);
        } catch (PulsarAdminException e) {
            System.err.println("--create topic error!--" + e);
        }
        //--考虑授权问题--
        //Set<AuthAction> actions1 = Sets.newHashSet(AuthAction.produce, AuthAction.consume);
        //admin.topics().grantPermission(topicName, role1, actions1);
    }


    /**
     * 创建Admin,后面写成单例
     */
    public static PulsarAdmin getAdmin() {
        try {
            PulsarAdminBuilderImpl pulsarAdminBuilder = new PulsarAdminBuilderImpl();
            PulsarAdmin admin = pulsarAdminBuilder.serviceHttpUrl(localClusterUrl).build();
            return admin;
        } catch (PulsarClientException e) {
            return null;
        }
    }

    /**
     * 关闭PulsarAdmin
     */
    public static void closePulsarAdmin() {
        getAdmin().close();
    }


    /**
     * 删除topic
     */
    public static void deleteTopic() {
        try {
            admin = getAdmin();
            admin.topics().deletePartitionedTopic(topicName);
        } catch (PulsarAdminException e) {
            System.err.println("--delete topic error!--" + e);
        }
    }

    /**
     * 删除 namespace
     */
    public static void deleteNamespace() {
        try {
            admin = getAdmin();
            admin.namespaces().deleteNamespace(nameSpaceName);
        } catch (PulsarAdminException e) {
            System.err.println("--delete namespace error!--" + e);
        }
    }

    /**
     * 删除 tenant
     */
    public static void deleteTenant() {
        try {
            admin = getAdmin();
            admin.tenants().deleteTenant(tenantName);
        } catch (PulsarAdminException e) {
            System.err.println("--delete tenant error!--" + e);
        }
    }


}