package hello.exception.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.exception.exception.UserException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserHandlerExceptionResolver implements HandlerExceptionResolver {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        try {
            if(ex instanceof UserException){
                String accept = request.getHeader("accept");
                response.setStatus(400);

                if(accept.equals("application/json")){
                    Map<String, Object> errorMap = new HashMap<>();
                    errorMap.put("ex", ex.getClass());
                    errorMap.put("message", ex.getMessage());
                    String result = mapper.writeValueAsString(errorMap);
                    response.setCharacterEncoding("utf-8");
                    response.setContentType("application/json");
                    response.getWriter().write(result);

                    return new ModelAndView();
                } else {
                    return new ModelAndView("/error/400");
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
