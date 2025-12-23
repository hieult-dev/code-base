# 1. Dùng nginx nhẹ
FROM nginx:alpine

# 2. Xóa config mặc định
RUN rm /etc/nginx/conf.d/default.conf

# 3. Copy config nginx của ta
COPY nginx.conf /etc/nginx/conf.d/default.conf

# 4. Copy build Vite (dist) vào nginx
COPY dist /usr/share/nginx/html

# 5. Mở cổng 80
EXPOSE 80

# 6. Chạy nginx
CMD ["nginx", "-g", "daemon off;"]
