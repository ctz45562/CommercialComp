# 代码说明：
'''
代码功能： 开放api接口，基于ChromeDriver爬取淘宝或京东平台商品列表数据
端口：5000
接口url：（淘宝）/searchInTaobao
         （京东）/searchInJingdong
参数：keyword 商品名
'''
# 声明第三方库/头文件
from selenium import webdriver
from selenium.common.exceptions import TimeoutException
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from pyquery import PyQuery as pq
import time
import pickle
import re
from flask import Flask, jsonify, request
from flask_cors import CORS

app = Flask(__name__)
CORS(app)
 
# 启动ChromeDriver服务
options = webdriver.ChromeOptions()
# 关闭自动测试状态显示 // 会导致浏览器报：请停用开发者模式
options.add_experimental_option("excludeSwitches", ['enable-automation'])
options.add_argument("--headless")
options.add_argument("--window-size=1920x1080") 
# 把chrome设为selenium驱动的浏览器代理；

# wait是Selenium中的一个等待类，用于在特定条件满足之前等待一定的时间(这里是15秒)。
# 如果一直到等待时间都没满足则会捕获TimeoutException异常



with open("cookies_taobao.pkl", "rb") as cookies_file:
    cookies_taobao = pickle.load(cookies_file)
with open("cookies_jingdong.pkl", "rb") as cookies_file:
    cookies_jingdong = pickle.load(cookies_file)

# 爬虫main函数
@app.route('/searchInTaobao',methods=['GET'])
def Taobao_main():
    driver = webdriver.Chrome(options=options)
    wait = WebDriverWait(driver,10)
    driver.maximize_window()
    KEYWORD = request.args.get('keyword', default='', type=str)
    try:
        print('正在搜索: '+KEYWORD)
        driver.get('https://www.taobao.com')
        driver.execute_cdp_cmd("Page.addScriptToEvaluateOnNewDocument",
                               {"source": """Object.defineProperty(navigator, 'webdriver', {get: () => undefined})"""})
        # 找到搜索“输入框”
        input = wait.until(EC.presence_of_element_located((By.CSS_SELECTOR, "#q")))
        # 找到“搜索”按钮
        submit = wait.until(
            EC.element_to_be_clickable((By.CSS_SELECTOR, '#J_TSearchForm > div.search-button > button')))
        # 输入框写入“关键词KeyWord”
        input.send_keys(KEYWORD)
        # 点击“搜索”按键
        submit.click()

        count = 1
        products = []
	    # 加载cookie
        for cookie in cookies_taobao:
            driver.add_cookie(cookie)
        driver.refresh()

        # 保存Cookie
        while True:
            time.sleep(0.1)
            if driver.execute_script("return document.readyState") == "complete":
                print("页面已加载完成")
                break

        with open("cookies_taobao.pkl", "wb") as cookies_file:
            pickle.dump(driver.get_cookies(), cookies_file)    
    	
        html = driver.page_source
        doc = pq(html)
        # 提取所有商品的共同父元素的类选择器
        items = doc('div.contentInner--xICYBlag > a').items()
 
        for item in items:
            # 将item转换为字符串
            item_str = str(item)
    
            # 定位商品图片url
            match = re.search(r'<img src=".*?".*?class="mainPic--[a-zA-Z0-9_]*?"/>', item_str)
            if match:
                img_url = pq(match.group()).attr('src')
            else:
                break
            # 定位标题
            match = re.search(r'<span class="">.*?</span>', item_str)
            title = pq(match.group()).text()       
            # 定位价格
            match = re.search(r'<span class="priceInt--[a-zA-Z0-9_]*?">.*?</span>', item_str)
            price_int = pq(match.group()).text()  
            match = re.search(r'<span class="priceFloat--[a-zA-Z0-9_]*?">.*?</span>', item_str)
            if match :
                price_float = pq(match.group()).text()
            else:
                price_float = ".0"
            price = price_int + price_float
            t_url = item.attr('href')
            # 定位店铺		
            match = re.search(r'<span class="shopNameText--[a-zA-z0-9_]*?">.*?</span>', item_str)
            shop = pq(match.group()).text()         
            match = re.search(r'<a class="shopName--[a-zA-z0-9_]*?".*?href=".*?">.*?</a>', item_str)
            shop_url = pq(match.group()).attr('href')           
            # 构建商品信息字典
            product = {
                'Num': count,
                'title': title,
                'price': price,
                'url': t_url,
                'imgUrl': img_url,
                'shop': shop,
                'shopUrl': shop_url,
                'platform': '淘宝'
            }
            products.append(product)
            count += 1    
        return jsonify(products)        
    except Exception as exc:
        print('Taobao_main函数报错:', exc)
	
@app.route('/searchInJingdong',methods=['GET'])		
def Jingdong_main():
    driver = webdriver.Chrome(options=options)
    wait = WebDriverWait(driver,10)
    driver.maximize_window()
    KEYWORD = request.args.get('keyword', default='', type=str)
    try:
        print('正在搜索: '+KEYWORD)
        driver.get('https://jd.com')
		# 加载cookie
        for cookie in cookies_jingdong:
            driver.add_cookie(cookie)
        driver.refresh()
		# 保存cookie
        with open("cookies_jingdong.pkl", "wb") as cookies_file:
            pickle.dump(driver.get_cookies(), cookies_file)  

        input = wait.until(EC.presence_of_element_located((By.CSS_SELECTOR, 'input#key')))
        submit = wait.until(
            EC.element_to_be_clickable((By.CSS_SELECTOR, 'button.button')))
        # 输入框写入“关键词KeyWord”
        input.send_keys(KEYWORD)
        # 点击“搜索”按键
        driver.execute_script("arguments[0].click();",submit)
        count = 1

        while True:
            time.sleep(0.1)
            if driver.execute_script("return document.readyState") == "complete":
                print("页面已加载完成")
                break
		
        products = []
        html = driver.page_source
        doc = pq(html)
        # 提取所有商品的共同父元素的类选择器
        items = doc('div#J_goodsList > ul.gl-warp > li').items()

        for item in items:
        
            t_url = item.find('div.p-img > a').attr('href')

            img_element = item.find('div.p-img > a > img')
            if img_element.attr('src'):
                img_url = img_element.attr('src')
            else:
               img_url = img_element.attr('data-lazy-img')
        
            price = item.find('div.p-price > strong').text().replace('￥','')

            title = item.find('div.p-name').text().replace('\n','')
		
            shop_element = item.find('div.p-shop > span > a')
            shop = shop_element.text()
            shop_url = shop_element.attr('href')
            # 构建商品信息字典
            product = {
                'Num': count,
                'title': title,
                'price': price,
                'url': t_url,
                'imgUrl': img_url,
                'shop': shop,
                'shopUrl': shop_url,
                'platform': '京东'
            }
            print(product)
            products.append(product)
            count += 1        
        return jsonify(products)
        
    except Exception as exc:
        print('Jingdong_main函数报错:', exc) 
 
 
 
if __name__ == '__main__':
    print('Server started.')
    app.run(port=5000)
