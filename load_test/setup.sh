# !/bin/bash
# 判断venv 文件夹是否存在，不存在则创建
if [ ! -d "venv" ]; then
   mkdir venv && python3.11 -m venv venv
fi
# 激活虚拟环境
source venv/bin/activate
# 安装依赖
pip install -r requirement.txt