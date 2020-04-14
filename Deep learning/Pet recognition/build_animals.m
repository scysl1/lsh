folder = 'animals';
f = fullfile(folder, '*.jpg');
filepaths = dir (f);

X=[];

for i = 1 : length(filepaths)
    image = imread(fullfile(folder,filepaths(i).name));
    I = rgb2gray(image);
    [x,y] = size(I);
    image2 = imresize(I, [200 200]);
    X(i,:) = reshape(image2,[1, 40000]);
end

cats = [1 ;0; 0];
dogs = [0 ;1 ;0];
pandas = [0; 0; 1];

y = [repmat(cats, 1, 1000) repmat(dogs, 1, 1000) repmat(pandas, 1, 1000)];
[Y,PS] = mapminmax(X,0,1);

