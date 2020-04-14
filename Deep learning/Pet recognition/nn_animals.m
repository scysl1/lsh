load('data.mat');
m = size(X, 1);
X = X';

net = patternnet([50,50]);

% divided into training, validation and testing simulate
net.divideParam.trainRatio = 0.8;
net.divideParam.valRatio = 0.5;
net.divideParam.testRatio = 0.2;


rand_indices = randperm(size(X, 2));

trainData = X(:, rand_indices(1:1500));
trainLabels = y(:, rand_indices(1:1500));
testData = X(:, rand_indices(1501:3000));
testLabels = y(:, rand_indices(1501:3000));

% train a neural network
net = train(net, trainData, trainLabels);

view(net);

preds = net(testData);
est = vec2ind(preds) - 1;
tar = vec2ind(testLabels) - 1;

accuracy = 100*length(find(est==tar))/length(tar);
fprintf('Accuracy rate is %.2f\n', accuracy);

% confusion matrix
plotconfusion(testLabels, preds)


fprintf('Program paused. Press enter to continue.\n');
pause;
    
%  To give you an idea of the network's output, you can also run
%  through the examples one at the a time to see what it is predicting.
%  Randomly permute examples
rp = randperm(m);

for i = 1:m
    % Display 
    fprintf('\nDisplaying Example Image\n');
    displayData(X(:, rp(i))');

    pred = vec2ind(net(X(:, rp(i)))) - 1;
    if(pred == 0)
        pred = 'cat';
    end
    if(pred == 1)
        pred = 'dog';
    end
    if(pred == 2)
        pred = 'panda';
    end
    lbl = vec2ind(y(:, rp(i))) - 1;
    if(lbl == 0)
        lbl = 'cat';
    end
    if(lbl == 1)
        lbl = 'dog';
    end
    if(lbl == 2)
        lbl = 'panda';
    end
    txtPred = strcat('Neural Network Prediction: ', pred);
    txtLabl = strcat('Label: ', lbl);
    text(10, 20, txtPred, 'FontSize', 20, 'Color', 'red');
    text(10, 40, txtLabl, 'FontSize', 20, 'Color', 'green');
    % fprintf('\nNeural Network Prediction: %d\n', pred);
    
    % Pause
    fprintf('Program paused. Press enter to continue.\n');
    pause;
end

