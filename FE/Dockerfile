# ===============================
# 1️⃣ Stage build: dùng Node để build Vite
# ===============================
FROM node:20-alpine AS build

# Thư mục làm việc
WORKDIR /app

# Copy file khai báo dependency trước (tối ưu cache)
COPY package*.json ./

# Cài dependency
RUN npm install

# Copy toàn bộ source code
COPY . .

# Build Vite (ra thư mục dist)
RUN npm run build


# ===============================
# 2️⃣ Stage runtime: nginx nhẹ
# ===============================
FROM nginx:alpine

# Xóa config mặc định
RUN rm /etc/nginx/conf.d/default.conf

# Copy nginx config của bạn
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Copy dist từ stage build sang nginx
COPY --from=build /app/dist /usr/share/nginx/html

# Mở cổng 80
EXPOSE 80

# Chạy nginx
CMD ["nginx", "-g", "daemon off;"]
