from locust import HttpUser, task
import random

class Consumer(HttpUser):
    # @task
    # def consume(self):
    #     # 生成一个随机整数，大小在100到200之间
    #     count = random.randint(100, 600)
    #     self.client.get(f"/api/consumer/hello?count={count}", name="/api/consumer/hello")
    #
    @task
    def dummy(self):
        count = random.randint(100, 600)
        self.client.get(f"/api/dummy?count={count}", name="/api/dummy")
