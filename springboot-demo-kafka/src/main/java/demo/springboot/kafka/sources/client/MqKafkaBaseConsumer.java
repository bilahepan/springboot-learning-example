package demo.springboot.kafka.sources.client;

/**
 *
 *@author: 文若[gaotc@tuya.com]
 *@date: 2019/7/29 下午11:36
 */
//public abstract class KafkaBaseConsumer<T> {
//
//	protected static Logger				logger	= LoggerFactory.getLogger(KafkaBaseConsumer.class);
//
//	private KafkaConsumerConfigServer	kafkaConsumerConfigServer;
//
//	public void setKafkaConsumerConfigServer(KafkaConsumerConfigServer kafkaConsumerConfigServer) {
//		this.kafkaConsumerConfigServer = kafkaConsumerConfigServer;
//	}
//
//	public KafkaConsumerConfig getConsumerConfig(String topic) {
//		if (kafkaConsumerConfigServer != null) {
//			return kafkaConsumerConfigServer.getConsumerConfig(topic);
//		}
//		return null;
//	}
//
//	/**
//	 * 根据key获取原生的kafka消费配置值
//	 *
//	 * @param key 原生配置key
//	 */
//	public Object getNativeConsumerValue(String key) {
//		return Objects.nonNull(kafkaConsumerConfigServer) && StringUtils.isNotEmpty(key)?
//				kafkaConsumerConfigServer.getNativeConsumerConfigValue(key): null;
//	}
//
//	protected void doMessage(ConsumerRecord<String, KafkaMqData<T>> record) {
//		KafkaConsumerConfig kafkaConsumerConfig = null;
//		if (record == null || record.value() == null) {
//			logger.error("[topic:" + record.topic() + "],消息值为null");
//			return;
//		}
//		record.value().setRt(System.currentTimeMillis() - record.value().getCt());
//		try {
//			if (logger.isDebugEnabled()) {
//				logger.debug("[" + record.topic() + "],value:" + record.value().toString() + ",offset=" + record.offset()
//						+ ",partition=" + record.partition() + ",key=" + record.key()+",threadId="+Thread.currentThread().getId());
//			}
//			kafkaConsumerConfig = getConsumerConfig(record.topic());
//			if (kafkaConsumerConfig == null || kafkaConsumerConfig.getConsumer() == null) {
//				logger.error("[topic:" + record.topic() + "],还没有配置,无法消费");
//				return;
//			}
//			IMqKafkaBizConsumer consumer = kafkaConsumerConfig.getConsumer();
//
//            KafkaMqData data = record.value();
//            Type[] types = consumer.getClass().getGenericInterfaces();
//            for (Type type : types) {
//                if (type instanceof ParameterizedType) {
//                    ParameterizedType pt = (ParameterizedType) type;
//                    if (pt.getRawType().equals(IMqKafkaBizConsumer.class)) {
//                        if (pt.getActualTypeArguments()[0] instanceof Class) {
//                            Class clazz = (Class) pt.getActualTypeArguments()[0];
//                            Object o = data.getData();
//                            if (o instanceof JSONObject && !(clazz.equals(JSONObject.class))) {
//                                JSONObject entity = ((JSONObject) o);
//                                entity.remove("@type");
//                                Object t = entity.toJavaObject(clazz);
//                                data.setData(t);
//                            }
//                        }
//                    }
//                }
//            }
//
//			boolean t = consumer.consumber(record.topic(), record.value(), record.partition(), record.offset(),
//					record.key());
//			if (!t) {
//				logger.warn("[topic:" + record.topic() + "],消费失败,但业务允许放弃:" + record.value().toString());
//			}
//			return;
//		} catch (Exception e) {
//			logger.error("[topic:" + record.topic() + "],执行跑出异常了:" + record.value().toString() + "kafka_error2:", e);
//			if (kafkaConsumerConfig.isIgnore()) {
//				return;
//			} else {
//				throw new RuntimeException(
//						"-1,[topic:" + record.topic() + "],异常重试:" + record.value().toString() + "kafka_error1:", e);
//			}
//		}
//	}
//
//}
public class MqKafkaBaseConsumer {
}