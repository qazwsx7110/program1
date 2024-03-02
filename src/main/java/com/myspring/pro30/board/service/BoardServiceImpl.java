package com.myspring.pro30.board.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.myspring.pro30.board.dao.BoardDAO;
import com.myspring.pro30.board.vo.ArticleVO;
import com.myspring.pro30.board.vo.ImageVO;

//������ ó��
@Service("boardService")
@Transactional(propagation = Propagation.REQUIRED)
public class BoardServiceImpl  implements BoardService{
	@Autowired
	BoardDAO boardDAO;
	
	//��� �� ��������
	public List<ArticleVO> listArticles() throws Exception{
		List<ArticleVO> articlesList =  boardDAO.selectAllArticlesList();
        return articlesList;
	}	
	
	 //���� �߰�
	@Override
	public int addNewArticle(Map articleMap) throws Exception{
		int articleNO = boardDAO.insertNewArticle(articleMap);
		articleMap.put("articleNO", articleNO);		
		List<ImageVO> imageFileList = (ArrayList)articleMap.get("imageFileList");		
		//�̹��� �߰�
		if(imageFileList!= null && imageFileList.size()!=0) {
		boardDAO.insertNewImage(articleMap);
		}		
		return articleNO;
	}
	
	//����߰�
	@Override
	public int addNewReplyArticle(Map articleMap) throws Exception{
		int articleNO = boardDAO.insertNewReplyArticle(articleMap);
		articleMap.put("articleNO", articleNO);
				
		return articleNO;
	}
	
	//������ �� ��������
	@Override
	public Map viewArticle(int articleNO) throws Exception {
		Map articleMap = new HashMap();
		ArticleVO articleVO = boardDAO.selectArticle(articleNO);
		List<ImageVO> imageFileList = boardDAO.selectImageFileList(articleNO);
		articleMap.put("article", articleVO);
		articleMap.put("imageFileList", imageFileList);
		return articleMap;
	}
	
	//�� ����
	@Override
	public void modArticle(Map articleMap) throws Exception {
		boardDAO.updateArticle(articleMap);					
        List<ImageVO> modimageFileList = (ArrayList)articleMap.get("modimageFileList");
		if(modimageFileList!= null && modimageFileList.size()!=0) {
			boardDAO.updateImage(articleMap);
		}					
	}
	
	//�� ����
	@Override
	public void removeArticle(int articleNO) throws Exception {
		boardDAO.deleteArticle(articleNO);
	}
	

	
}
