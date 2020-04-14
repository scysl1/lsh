%% Image Acquire
% Read the image from disk. To read different pictures, you can change the
% input picture as you want :)
pic1 = imread('StackNinja3.bmp');

%% Color Conversion: From RGB to HSV 
% Change the color space from GRB to HSV 
pic1_hsv = rgb2hsv(pic1);

%% Extract the Hue channel
% After analysis, extract hue channel as it contains most of important
% information and it is easy to deal with segementation
pic1_h_channel = pic1_hsv(:,:,1);

%% Noise Reduction
% Before binary image processing, it is important to conduct necessary
% noise reduction. Here, two noise recutions approaches are combined for a
% better result. 
pic1_NR = medfilt2(pic1_h_channel)
pic1_NR = filter2(fspecial('Gaussian', 3, 1), pic1_NR)
figure
imshow(pic1_NR)
impixelregion
%% Thresholding 
% By visualising area of nuclei, 0.17 was chose as the threshold 
pic1_thresholding = pic1_NR > 0.17

%% Dilation
% Closing is conducted. Dilation is done here to first cover the negative impact caused by
% thresholding. 
se90 = strel('line', 1,90);
se0 = strel('line', 1,0);
pic1_dila = imdilate(pic1_thresholding,[se90 se0]);

%% Errosion
% Erosion conducted here is designed for eliminate dilation effect on noise
%
seD = strel('disk',1);
pic1_ero = imerode(pic1_dila, seD);
pic1_ero = imerode(pic1_ero, seD);
pic1_final = pic1_ero
figure 
imshow(pic1_final);

%% Counting Nuclei
% Count the independent connected area as nuclei. 
[L, n] = bwlabel(pic1_final, 8)

%% Mask overlay 
% Overlay the mask on original picture to help visualize the effect of
% segmentation. It also helps to evaluate the result.
figure
imshow(labeloverlay(pic1,uint8(pic1_final)));
title("The number of nuclei is: " + n);

