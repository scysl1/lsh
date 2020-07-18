load('data.mat');
stdr = std(X);              
[n,m]= size(X);               
sddata = X./stdr(ones(m,1));     
[p,princ,egenvalue]=pca(sddata);  
p=p(:,1:3);                        
sc=princ(:,1:3);                     
egenvalue;                              
per=100*egenvalue/sum(egenvalue);      
