const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const CopyPlugin = require("copy-webpack-plugin");
const WorkboxWebpackPlugin = require("workbox-webpack-plugin");
module.exports = {

  mode: 'development',
  entry: {
    bundle: path.resolve(__dirname, "src/index.js")

  },
  output: {
    path: path.resolve(__dirname, 'dist'),
    filename: '[name][contenthash].js',
    clean: true,
    assetModuleFilename: '[name][ext]',
  },
  devtool: 'source-map',
  devServer: {
    static: {
      directory: path.resolve(__dirname, 'dist'),
    },
    port: 3030,
    open: false,
    hot: true,
    compress: true,
  },
  module: {
    rules: [
      {
        test: /\.css$/,
        use: ['style-loader', 'css-loader'],
      },
          {
            test: /\.(?:js|mjs|cjs)$/,
            exclude: /node_modules/,
            include: path.resolve(__dirname, 'pages'),
            use: {
              loader: 'babel-loader',
              options: {
                presets: [
                  ['@babel/preset-react', { targets: "defaults" }]
                ],
              }
            }
          },
      {
        test: /\.(png|svg|jpg|jpeg|gif)$/i,
        type: 'asset/resource',
      },
    ]
  },
  plugins: [
    new HtmlWebpackPlugin({
      title: 'VitaTrac',
      filename: 'index.html',
      template: 'src/index.html'
    }),
    new CopyPlugin({
      patterns: [
        {from: path.resolve(__dirname, 'src', 'manifest.json'), to: path.resolve(__dirname, 'dist', 'manifest.json')},
        {from: path.resolve(__dirname, 'src', 'icons'), to: path.resolve(__dirname, 'dist', 'icons')}
        
      ]
    }),
    new WorkboxWebpackPlugin.InjectManifest({
      swSrc: path.resolve(__dirname, 'src', 'service-worker.js'),
      swDest: path.resolve(__dirname, 'dist', 'service-worker.js')
    })
  ]
}


// need copy plugin syntax
