# multi_tenant_springbatch_demo
## spring batch demo with multi tenant

 * This is a spring batch demo with multi tenant. It uses multi thread to run diffirent job with different DBs.
 * In this app, I only want to provide the method to use multi tenant in batch. So there is no biz logic.
 
 * It uses jasypt in application.properties too, if you don't want to use, you need to remove this:
   * jasypt.encryptor.password=helloworld
 
 * Before you run it, you should change the datasource properties by using your. 
 * You also need to run schema_customer.sql at your mysql db.
